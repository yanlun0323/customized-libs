package com.customized.libs.core.libs.druid.druid.parser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.odps.parser.OdpsStatementParser;
import com.alibaba.druid.sql.dialect.odps.visitor.OdpsSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.stream.Collectors;

public class DruidParser {


    public static void main(String[] args) {
        String dbType = JdbcConstants.ODPS;

        SQLParser sqlParser = new SQLParser(sql, dbType);
        OdpsStatementParser parser = new OdpsStatementParser(sqlParser.getLexer().text);
        SQLStatement sqlStatement = parser.parseStatement();


        OdpsSchemaStatVisitor visitor = new OdpsSchemaStatVisitor();
        sqlStatement.accept(visitor);
        System.out.println(visitor.getTables());

        System.out.println("");

        // Select Table
        List<String> data = visitor.getTables().entrySet().stream().filter(c -> "Select".equals(c.getValue().toString()))
                .map(c -> c.getKey().getName()).collect(Collectors.toList());

        System.out.println(data);

        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, dbType);
        System.out.println(visitor.getConditions());

        System.out.println("");

        for (TableStat.Condition condition : visitor.getConditions()) {
            if (condition.getColumn().getName().contains("date")) {
                System.out.println(condition.getValues());
            }
        }

        // System.out.println(sqlStatements);
    }

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
}
