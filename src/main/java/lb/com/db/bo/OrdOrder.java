package lb.com.db.bo;

import java.util.Date;

public class OrdOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.goods_id
     *
     * @mbggenerated
     */
    private Integer goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.quantity
     *
     * @mbggenerated
     */
    private Integer quantity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.price
     *
     * @mbggenerated
     */
    private Double price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.amt
     *
     * @mbggenerated
     */
    private Double amt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.use_time
     *
     * @mbggenerated
     */
    private Date useTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.remark1
     *
     * @mbggenerated
     */
    private String remark1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.created_by
     *
     * @mbggenerated
     */
    private Integer createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ord_order.created_time
     *
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.id
     *
     * @return the value of ord_order.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.id
     *
     * @param id the value for ord_order.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.goods_id
     *
     * @return the value of ord_order.goods_id
     *
     * @mbggenerated
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.goods_id
     *
     * @param goodsId the value for ord_order.goods_id
     *
     * @mbggenerated
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.quantity
     *
     * @return the value of ord_order.quantity
     *
     * @mbggenerated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.quantity
     *
     * @param quantity the value for ord_order.quantity
     *
     * @mbggenerated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.price
     *
     * @return the value of ord_order.price
     *
     * @mbggenerated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.price
     *
     * @param price the value for ord_order.price
     *
     * @mbggenerated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.amt
     *
     * @return the value of ord_order.amt
     *
     * @mbggenerated
     */
    public Double getAmt() {
        return amt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.amt
     *
     * @param amt the value for ord_order.amt
     *
     * @mbggenerated
     */
    public void setAmt(Double amt) {
        this.amt = amt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.use_time
     *
     * @return the value of ord_order.use_time
     *
     * @mbggenerated
     */
    public Date getUseTime() {
        return useTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.use_time
     *
     * @param useTime the value for ord_order.use_time
     *
     * @mbggenerated
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.remark1
     *
     * @return the value of ord_order.remark1
     *
     * @mbggenerated
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.remark1
     *
     * @param remark1 the value for ord_order.remark1
     *
     * @mbggenerated
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.created_by
     *
     * @return the value of ord_order.created_by
     *
     * @mbggenerated
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.created_by
     *
     * @param createdBy the value for ord_order.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ord_order.created_time
     *
     * @return the value of ord_order.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ord_order.created_time
     *
     * @param createdTime the value for ord_order.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}