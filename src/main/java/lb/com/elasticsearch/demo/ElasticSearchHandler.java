package lb.com.elasticsearch.demo;

import lb.com.elasticsearch.demo.vo.MedicineVo;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST1), 9300));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * 建立索引,索引建立好之后,会在elasticsearch-0.20.6\data\elasticsearch\nodes\0创建所以你看
     * @param indexName  为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
     * @param indexType  Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
     * @param jsondata     json格式的数据集合
     *
     * @return
     */
    public void createIndexResponse(String indexName, String indexType, List<String> jsondata){
        //创建索引库 需要注意的是.setRefresh(true)这里一定要设置,否则第一次建立索引查找不到数据
        IndexRequestBuilder requestBuilder = client.prepareIndex(indexName, indexType).setRefresh(true);
        for(int i=0; i<jsondata.size(); i++){
//            requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
            IndexResponse response = requestBuilder.setSource(jsondata.get(i)).get();
            System.out.println("set status: "+response.isCreated());
        }

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
     * @param sKey
     * @param sValue
     * @return
     */
    public List<MedicineVo>  searcher(String sKey, String sValue){
        List<MedicineVo> list = new ArrayList<MedicineVo>();
        SearchRequestBuilder srb1 = client.prepareSearch().setQuery(QueryBuilders.matchQuery(sKey, sValue)).addSort("id", SortOrder.DESC).setFrom(0).setSize(100);
        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
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


    public static void main(String[] args) {
        ElasticSearchHandler esHandler = new ElasticSearchHandler();
        //待存储内容
//        List<String> jsondata = DataFactory.getInitJsonData();
        String indexname = "indexdemo";
        String type = "typedemo";
        //索引内容--入库
//        esHandler.createIndexResponse(indexname, type, jsondata);
        //查询条件
        List<MedicineVo> result = esHandler.searcher("name", "感冒");
        for(int i=0; i<result.size(); i++){
            MedicineVo MedicineVo = result.get(i);
            System.out.println("(" + MedicineVo.getId() + ")药品名称:" +MedicineVo.getName() + "\t\t" + MedicineVo.getFunction());
        }
    }
    
}
