package com.customized.libs.shardingsphere.dao.generate;

import com.customized.libs.shardingsphere.entity.generate.TSpringScheduledCron;
import com.customized.libs.shardingsphere.entity.generate.TSpringScheduledCronExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TSpringScheduledCronMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int countByExample(TSpringScheduledCronExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int deleteByExample(TSpringScheduledCronExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int deleteByPrimaryKey(Integer cronId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int insert(TSpringScheduledCron record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int insertSelective(TSpringScheduledCron record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    List<TSpringScheduledCron> selectByExample(TSpringScheduledCronExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    TSpringScheduledCron selectByPrimaryKey(Integer cronId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int updateByExampleSelective(@Param("record") TSpringScheduledCron record, @Param("example") TSpringScheduledCronExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int updateByExample(@Param("record") TSpringScheduledCron record, @Param("example") TSpringScheduledCronExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int updateByPrimaryKeySelective(TSpringScheduledCron record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_spring_scheduled_cron
     *
     * @mbggenerated Wed Oct 21 09:22:23 CST 2020
     */
    int updateByPrimaryKey(TSpringScheduledCron record);
}