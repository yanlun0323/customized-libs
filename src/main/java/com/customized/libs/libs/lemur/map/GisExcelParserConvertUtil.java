package com.customized.libs.libs.lemur.map;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.customized.libs.libs.lemur.map.entity.DataRow;
import io.lemur.map.area.AreaSearchUtil;
import io.lemur.map.model.ProvinceCityModel;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yan
 */
public class GisExcelParserConvertUtil {

    private static Map<String, ProvinceCityModel> PROVINCE_CITY_MAP = new TreeMap<>();

    static {
        initProvinceCityMap();
    }

    private static void initProvinceCityMap() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(GisExcelParserConvertUtil.class
                .getClassLoader().getResourceAsStream("datasource/unionProvCityMap")))) {
            String dataRow;
            while ((dataRow = br.readLine()) != null) {
                String[] components = dataRow.split(",");
                ProvinceCityModel entity = new ProvinceCityModel();
                entity.setCityId(components[0].substring(0, 4));
                entity.setCityName(components[1]);
                entity.setProvinceId(components[2].substring(0, 2));
                entity.setProvinceName(components[3]);
                PROVINCE_CITY_MAP.put(components[0].substring(0, 4), entity);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public static void convertAndExportExcel(String importPath, String exportPath) {
        // 设置文件路径
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        List<DataRow> dataRows = ExcelImportUtil.importExcel(
                new File(PoiPublicUtil.getWebRootPath(importPath)), DataRow.class, importParams);

        // 调用Map工具类-解析地理位置信息
        List<DataRow> converted = new ArrayList<>(dataRows.size());
        dataRows.parallelStream().forEach(dataRow -> {
            String data = AreaSearchUtil.getCityCodeByGcj(dataRow.getLon(), dataRow.getLat(), Boolean.TRUE);
            ProvinceCityModel model = PROVINCE_CITY_MAP.get(data);
            dataRow.setCityName(model.getCityName());
            dataRow.setProvName(model.getProvinceName());
            converted.add(dataRow);
        });

        // Excel导出
        ExportParams exportParams = new ExportParams();
        Workbook exported = ExcelExportUtil.exportExcel(exportParams, DataRow.class, converted);

        try (FileOutputStream outputStream =
                     new FileOutputStream(PoiPublicUtil.getWebRootPath(exportPath))) {
            exported.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
