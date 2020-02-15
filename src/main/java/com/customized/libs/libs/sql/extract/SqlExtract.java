package com.customized.libs.libs.sql.extract;

public class SqlExtract {

    public static final String sql = "INSERT OVERWRITE TABLE mid_coach_feeds_news_view_source PARTITION (dt='20190430',basis_type=3)\n" +
            "SELECT if(client_platform is null,-1,client_platform) as client_platform\n" +
            ",if(client_id is null,-1,client_id) as client_id\n" +
            ",if(mode is null,-1,mode) as mode\n" +
            ",if(post_type is null,-1,post_type) as post_type\n" +
            ",if(news_source is null,-1,news_source) as news_source\n" +
            ",if(info_source is null,-1,info_source) as info_source\n" +
            ",if(view_pv is null,0,view_pv) as view_pv\n" +
            ",if(view_uv is null,0,view_uv) as view_uv\n" +
            ",if(click_pv is null,0,click_pv) as click_pv\n" +
            ",if(click_uv is null,0,click_uv) as click_uv\n" +
            ",null as browse_pv,null as browse_uv\n" +
            ",null as read_duration,null as report_read_user_num\n" +
            ",'2019-04-01' as month_ymd \n" +
            "from(\n" +
            "SELECT client_id,client_platform,mode,post_type,news_source,info_source\n" +
            ",sum(view_pv) as view_pv,count(DISTINCT if(view_pv>0,user_id,null)) as view_uv\n" +
            ",sum(click_pv) as click_pv,count(DISTINCT if(click_pv>0,user_id,null)) as click_uv \n" +
            "from(\n" +
            "SELECT result.client_id,result.client_platform,result.mode,result.post_type,result.news_source,result.info_source\n" +
            ",user_id,view_pv,click_pv,is_effective\n" +
            "from(\n" +
            "    SELECT temp_pv.client_id,client_platform,temp_pv.user_id,mode,post_type,news_source,info_source\n" +
            "    ,view_pv,click_pv\n" +
            "\t,CASE \n" +
            "        when invalid_ymd is null then 1\n" +
            "        when first_ymd>='20190401' and is_invalid=2 then 1 \n" +
            "        when first_ymd<'20190401' and invalid_ymd>='20190401' then 1\n" +
            "        else 2\n" +
            "    end as is_effective \n" +
            "    from( \n" +
            "        SELECT client_id,client_platform,user_id,mode,content_type as post_type,news_source,position as info_source\n" +
            "        ,count(if(rq_action=1,1,null)) as view_pv\n" +
            "        ,count(if(rq_action=2,1,null)) as click_pv\n" +
            "        from meetyou_pro.dw_news_user_bgview_action_day_i\n" +
            "        where SUBSTR(dt,1,6)='201904' and client_id in (1,2)\n" +
            "        group by client_id,client_platform,user_id,mode,content_type,news_source,position \n" +
            "    )temp_pv \n" +
            "    LEFT OUTER JOIN (\n" +
            "        SELECT client_id, user_id, TO_CHAR(first_access_time, 'yyyymmdd') AS first_ymd\n" +
            "\t\t\t, IF(created_invalid = 1, 1, 2) AS is_invalid \n" +
            "\t\t\t, invalid_date AS invalid_ymd \n" +
            "        from meetyou_pro.dw_active_user_wide_coach\n" +
            "        where dt='20190430' and client_id in (1,2)  \n" +
            "    )temp_wide\n" +
            "    on temp_pv.user_id = temp_wide.user_id and temp_pv.client_id = temp_wide.client_id \n" +
            ")temp_view \n" +
            " LATERAL VIEW meetyou_pro:all_expanded_6 (client_id,client_platform,mode,post_type,news_source,info_source) result AS client_id,client_platform,mode,post_type,news_source,info_source \n" +
            ")temp \n" +
            "where is_effective=1\n" +
            "group by client_id,client_platform,mode,post_type,news_source,info_source\n" +
            ")temp_all;";

    public static final String sql2 = "\n" +
            "SELECT\n" +
            "\tt.*, tt.RULE_TYPE,tt.INVOKE_SERVICE,tt.UI_COMPONENT_DEFINITION\n" +
            "FROM\n" +
            "\tT_RISK_RULE t \n" +
            "LEFT JOIN T_RISK_RULE_CONFIG tt ON t.RULE_CONFIG_ID = tt.CONFIG_ID\n" +
            "WHERE t.USER_TYPE = '1'\n" +
            "AND t.TRADE_TYPE = 'POS'\n" +
            "AND t.STATE = 'NORMAL';";

    public static void main(String[] args) {
        System.out.println(SqlExtractUtils.extract(sql, new String[]{"from", "partition", "join"}));
    }
}
