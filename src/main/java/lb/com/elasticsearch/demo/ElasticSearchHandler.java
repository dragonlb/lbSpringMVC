package lb.com.elasticsearch.demo;

import lb.com.elasticsearch.demo.vo.MedicineVo;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libing on 2016/3/22.
 */
public class ElasticSearchHandler {

    private Client client;

    private static final String ES_HOST1 = "127.0.0.1";

    public ElasticSearchHandler(){
        //使用本机做为节点
        this(ES_HOST1);
    }

    public ElasticSearchHandler(String ipAddress){
        //集群连接超时设置
        /*
              Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout", "10s").build();
            client = new TransportClient(settings);
         */
//        client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ipAddress, 9300));
        Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch")
//                .put("client.transport.sniff", true)
                .build();
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipAddress), 9300));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Client getClient(){
        return client;
    }

    /**
     * 建立索引,索引建立好之后,会在elasticsearch-0.20.6\data\elasticsearch\nodes\0创建所以你看
     * @param indexName  为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
     * @param indexType  Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
     * @param jsondata     json格式的数据集合
     *
     * @return
     */
    public int createIndexResponse(String indexName, String indexType, List<String> jsondata){
        int retCount = 0;
        //创建索引库 需要注意的是.setRefresh(true)这里一定要设置,否则第一次建立索引查找不到数据
        IndexRequestBuilder requestBuilder = client.prepareIndex(indexName, indexType).setRefresh(true);
        for(int i=0; i<jsondata.size(); i++){
            IndexResponse response = requestBuilder.setSource(jsondata.get(i)).get();
            if(response.isCreated())    retCount++;
            System.out.println("set status: "+response.isCreated());
        }
        return retCount;
    }

    public int createByBulk(List<String> jsondata){
        int retCount = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk().setRefresh(true);
        for(int i=0;i<jsondata.size();i++){
            bulkRequest.add(client.prepareIndex("lb", "test", ""+i).setSource(jsondata.get(i))).get();
        }
        BulkResponse responses = bulkRequest.get();
        for(BulkItemResponse itemResponse: responses.getItems()){
            if(!itemResponse.isFailed()){
                retCount++;
            }
        }
        System.out.println("set index by bulk : "+retCount);
        return retCount;
    }

    /**
     * 创建索引
     * @param indexName
     * @param jsondata
     * @return
     */
    public IndexResponse createIndexResponse(String indexName, String type,String jsondata){
        IndexResponse response = client.prepareIndex(indexName, type)
                .setSource(jsondata)
                .execute()
                .actionGet();
        return response;
    }

    /**
     * 执行搜索
     * @param esKey
     * @param esValue
     * @return
     */
    public List<MedicineVo>  searcher(String esKey, String esValue){
        List<MedicineVo> list = new ArrayList<MedicineVo>();
//        SearchRequestBuilder srb1 = client.prepareSearch().setQuery(QueryBuilders.queryStringQuery("镇痛")).setSize(100);
        SearchRequestBuilder srb2 = client.prepareSearch().setQuery(QueryBuilders.matchQuery(esKey, esValue))
//                .addSort("id", SortOrder.DESC)
                .setFrom(0).setSize(100);
        MultiSearchResponse sr = client.prepareMultiSearch()
//                .add(srb1)
                .add(srb2)
                .execute().actionGet();
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
            for(SearchHit hit : response.getHits()){
                Integer id = (Integer)hit.getSource().get("id");
                String name =  (String) hit.getSource().get("name");
                String function =  (String) hit.getSource().get("funciton");
                list.add(new MedicineVo(id, name, function));
            }
        }
        client.close();
        System.out.println("查询到记录数 =" + nbHits+" / "+list.size());
        return list;
    }


    public static void initIndex(ElasticSearchHandler esHandler, List<String> initList){
        String indexname = "indexdemo";
        String type = "typedemo";
        //索引内容--入库
        int successCount = esHandler.createIndexResponse(indexname, type, initList);
        System.out.println("Init index success " + successCount + " / " + initList.size());
    }

    public static void updateIndex(ElasticSearchHandler esHandler, String esKey, String esValue){
//        UpdateRequestBuilder urBuilder = esHandler.getClient().prepareUpdate("indexdemo", "typedemo", "id");
        UpdateRequest updateRequest = new UpdateRequest("lb", "test", "1");
        try {
            updateRequest.doc(XContentFactory.jsonBuilder()
                    .startObject()
                    .field(esKey, esValue)
                    .endObject());
            UpdateResponse response =esHandler.getClient().update(updateRequest).get();
            System.out.println(esKey + "=" + esValue + " updated " + response.isCreated());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void deleteIndex(ElasticSearchHandler esHandler){
        DeleteRequest deleteRequest = new DeleteRequest("lb", "test", "0");
        try {
            DeleteResponse response =esHandler.getClient().delete(deleteRequest).get();
            System.out.println("delete " + response.isFound());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void deleteBatchIndex(ElasticSearchHandler esHandler){
        BulkRequestBuilder bulkRequest = esHandler.getClient().prepareBulk().setRefresh(true);
        try {
            int deleteCount = 0;
            bulkRequest.add(esHandler.getClient().prepareDelete("lb", "test", "1") );
            bulkRequest.add(esHandler.getClient().prepareDelete("lb", "test", "2") );
            bulkRequest.add(esHandler.getClient().prepareDelete("lb", "test", "3") );
            bulkRequest.add(esHandler.getClient().prepareDelete("lb", "test", "4") );
            BulkResponse responses = bulkRequest.get();
            for(BulkItemResponse itemResponse: responses.getItems()){
                if(!itemResponse.isFailed()){
                    deleteCount++;
                }
            }
            System.out.println("delete " + deleteCount+" records.");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ElasticSearchHandler esHandler = new ElasticSearchHandler(ES_HOST1);
        //待存储内容
//        List<String> jsondata = DataFactory.getInitJsonData();
//        esHandler.initIndex(esHandler, jsondata);
//        esHandler.createByBulk(jsondata);
//        esHandler.updateIndex(esHandler, "name", "感冒 鼻涕");
//        esHandler.deleteIndex(esHandler);
//        esHandler.deleteBatchIndex(esHandler);
        //查询条件
        List<MedicineVo> result = esHandler.searcher("name", "感冒");
        for(int i=0; i<result.size(); i++){
            MedicineVo MedicineVo = result.get(i);
            System.out.println("(" + MedicineVo.getId() + ")药品名称:" +MedicineVo.getName() + "\t\t" + MedicineVo.getFunction());
        }
    }
    
}
