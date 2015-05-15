package lb.com.db.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by libing on 2015/5/13.
 */
public class MyBatisBaseDao<T> {
    protected SqlSessionTemplate sqlSession;
    protected boolean externalSqlSession;

    private static final Log LOG = LogFactory.getLog(MyBatisBaseDao.class);

    private int maxRows = 1001;
    private int maxRowsForReport = 50000;
    private String namespaceName;

    public MyBatisBaseDao() {

    }

    public MyBatisBaseDao(String namespaceName) {
        super();
        this.namespaceName = namespaceName;
    }

    public SqlSession getSqlSession() {
        return this.sqlSession;
    }

    @Autowired(required = false)
    public final void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        if (!this.externalSqlSession) {
            this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    private String createStatementName(String id) {
        return namespaceName + "." + id;
    }

    public int insert(T entity){
        return insert("insert", entity);
    }

    public int update(T entity){
        return update("update", entity);
    }

    public int delete(T entity){
        return delete("delete", entity);
    }

    protected int insert(String key, Object object) {
        if (object != null) {
            return getSqlSession().insert(createStatementName(key), object);
        }
        return 0;
    }

    protected int insertFromOtherTable(String key, Map<String, Object> paramMap) {
        if (paramMap != null) {
            return getSqlSession().insert(createStatementName(key),paramMap);
        }
        return 0;
    }

    protected int update(String key, Object object) {
        if (object != null) {
            return getSqlSession().update(createStatementName(key), object);
        }
        return 0;
    }

    protected int delete(String key, Serializable id) {
        if (id != null) {
            return getSqlSession().delete(createStatementName(key), id);
        }
        return 0;
    }

    protected int delete(String key, Object object) {
        if (object != null) {
            return getSqlSession().delete(createStatementName(key), object);
        }
        return 0;
    }

    protected int deleteAll(String key, Map<String, Object> paramMap) {
        if (paramMap != null) {
            return getSqlSession().delete(createStatementName(key),paramMap);
        }
        return 0;
    }
    protected T get(String key, Object params) { // update by yongchun
        List<T> list = this.getList(key, params);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        if (list.size() == 1) {
            return list.get(0);
        }

        if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: "+ list.size());
        }

        return null;
    }

    /**
     * 重载一个无参数的get方法，供vst_search使用
     * @author Lb
     * @param key
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    protected T get(String key) {
        return (T) getSqlSession().selectOne(createStatementName(key));
    }

    protected  List<T> getList(String key) {
        return getSqlSession().selectList(createStatementName(key));
    }

    protected  List<T> getList(String key, Object params) {
        if (params != null) {
            return getSqlSession().selectList(createStatementName(key), params);
        } else {
            return null;
        }
    }

    // 允许参数传入null
    public List<T> getListFree(String key, Object params) {
        return getSqlSession().selectList(createStatementName(key), params);
    }

    protected List<T> queryForList(String statementName) throws DataAccessException {
        return queryForList(statementName, null);
    }

    protected List<T> queryForList(final String statementName, final Object parameterObject) throws DataAccessException {
        if (parameterObject != null) {
            List<T> result = getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(0, maxRows));
            if ((result != null) && (result.size() == maxRows)) {
                LOG.warn("SQL Exception: result size is greater than the max rows, " + namespaceName + "." + statementName);
            }
            return result;
        } else {
            return null;
        }
    }

    protected List<T> queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {

        if ((maxResults - skipResults) >= maxRows) {
            maxResults = skipResults + maxRows;
            LOG.warn("SQL Exception: result size is greater than the max rows, " + createStatementName(statementName));
        }

        return queryForList(statementName, null, skipResults, maxResults);
    }

    protected List<T> queryForList(final String statementName, final Object parameterObject, final int skipResults, final int maxResults) throws DataAccessException {

        int tempMaxResults = maxResults;
        if ((maxResults - skipResults) >= maxRows) {
            tempMaxResults = skipResults + maxRows;
            LOG.warn("SQL Exception: result size is greater than the max rows, " + createStatementName(statementName));
        }
        return getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(skipResults, tempMaxResults));
    }

    // 数据量比较大的报表导出请用这个接口
    protected List<T> queryForListForReport(String statementName) throws DataAccessException {
        return queryForListForReport(statementName, null);
    }

    // 数据量比较大的报表导出请用这个接口
    protected List<T> queryForListForReport(final String statementName, final Object parameterObject) throws DataAccessException {

        List<T> result = getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(0, maxRowsForReport));

        if ((result != null) && (result.size() == maxRowsForReport)) {
            LOG.warn("SQL Exception: result size is greater than the max rows, " + statementName);
        }
        return result;
    }

    // 数据量比较大的报表导出请用这个接口
    protected List<T> queryForList(final String statementName, final Object parameterObject, final boolean isForReportExport) throws DataAccessException {

        int maxRowsTemp = maxRows;
        if (isForReportExport) {
            maxRowsTemp = maxRowsForReport;
        }

        List<T> result = getSqlSession().selectList(createStatementName(statementName), parameterObject, new RowBounds(0, maxRowsTemp));
        if ((result != null) && (result.size() == maxRowsTemp)) {
            LOG.warn("SQL Exception: result size is greater than the max rows, " + statementName);
        }
        return result;
    }
}
