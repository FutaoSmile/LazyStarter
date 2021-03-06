package com.lazy.api.generator.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lazy.api.generator.model.ApiController;
import com.lazy.api.generator.model.ApiInfo;
import com.lazy.httpclient.GetRequest;
import com.lazy.tools.DateTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author futao
 * Created on 2019-05-15.
 */
@Slf4j
public class ApiGenerator {

    /**
     * 数据源为Swagger
     */
    public static class SwaggerGenerator {
        /**
         * 生成
         *
         * @param fileName
         * @param url
         */
        public static ApiInfo gen(String fileName, String swaggerApiUrl) throws IllegalArgumentException {
            long start = System.currentTimeMillis();
            if (fileName == null || !Pattern.compile("\\S+(.md)$").matcher(fileName).matches()) {
                log.error("文件名必须不为空且以.md结尾，请重新确认您的输入");
                throw new IllegalArgumentException("文件名必须不为空且以.md结尾，请重新确认您的输入");
            }
            File file = new File(fileName);
            GetRequest getRequest = new GetRequest(swaggerApiUrl);
            String result = getRequest.send().getResult();
            ApiInfo apiInfo = SwaggerDataLoader.loadData(result);
            ApiGenerator.genInfo(apiInfo, file);
            ApiGenerator.genPaths(apiInfo, file);
            log.info(">>> 生成成功，耗时[{}]毫秒", System.currentTimeMillis() - start);
            return apiInfo;
        }


        public static ApiInfo gen(String fileName, String swaggerApiUrl, String userName, String password) throws IllegalArgumentException {
            long start = System.currentTimeMillis();
            if (fileName == null || !Pattern.compile("\\S+(.md)$").matcher(fileName).matches()) {
                log.error("文件名必须不为空且以.md结尾，请重新确认您的输入");
                throw new IllegalArgumentException("文件名必须不为空且以.md结尾，请重新确认您的输入");
            }
            File file = new File(fileName);
            GetRequest getRequest = new GetRequest(swaggerApiUrl);
            getRequest.addCredentials(userName, password);
            String result = getRequest.send().getResult();
            ApiInfo apiInfo = SwaggerDataLoader.loadData(result);
            ApiGenerator.genInfo(apiInfo, file);
            ApiGenerator.genPaths(apiInfo, file);
            log.info(">>> 生成成功，耗时[{}]毫秒", System.currentTimeMillis() - start);
            return apiInfo;
        }

        /**
         * 多数据源
         *
         * @param fileName
         * @param swaggerApiUrl
         * @return
         */
        public static ArrayList<ApiInfo> gen(String fileName, String[] swaggerApiUrl) throws IllegalArgumentException {
            long start = System.currentTimeMillis();
            if (fileName == null || !Pattern.compile("\\S+(.md)$").matcher(fileName).matches()) {
                log.error("文件名必须不为空且以.md结尾，请重新确认您的输入");
                throw new IllegalArgumentException("文件名必须不为空且以.md结尾，请重新确认您的输入");
            }
            ArrayList<ApiInfo> apiInfos = new ArrayList<>(swaggerApiUrl.length);
            File file = new File(fileName);
            for (String s : swaggerApiUrl) {
                GetRequest getRequest = new GetRequest(s);
                String result = getRequest.send().getResult();
                ApiInfo apiInfo = SwaggerDataLoader.loadData(result);
                ApiGenerator.genInfo(apiInfo, file);
                ApiGenerator.genPaths(apiInfo, file);
            }
            log.info(">>> 生成成功，耗时[{}]毫秒", System.currentTimeMillis() - start);
            return apiInfos;
        }

        /**
         * 多数据源
         *
         * @param fileName
         * @param swaggerApiUrl
         * @return
         */
        public static ArrayList<ApiInfo> gen(String fileName, String userName, String password, String[] swaggerApiUrl) throws IllegalArgumentException {
            long start = System.currentTimeMillis();
            if (fileName == null || !Pattern.compile("\\S+(.md)$").matcher(fileName).matches()) {
                log.error("文件名必须不为空且以.md结尾，请重新确认您的输入");
                throw new IllegalArgumentException("文件名必须不为空且以.md结尾，请重新确认您的输入");
            }
            ArrayList<ApiInfo> apiInfos = new ArrayList<>(swaggerApiUrl.length);
            File file = new File(fileName);
            for (String s : swaggerApiUrl) {
                GetRequest getRequest = new GetRequest(s);
                getRequest.addCredentials(userName, password);
                String result = getRequest.send().getResult();
                ApiInfo apiInfo = SwaggerDataLoader.loadData(result);
                ApiGenerator.genInfo(apiInfo, file);
                ApiGenerator.genPaths(apiInfo, file);
            }
            log.info(">>> 生成成功，耗时[{}]毫秒", System.currentTimeMillis() - start);
            return apiInfos;
        }
    }

    /**
     * 加载path信息
     *
     * @return
     */
    private static void genPaths(ApiInfo apiInfo, File file) {
        List<ApiController> apiControllerList = apiInfo.getApiControllerList();
        apiControllerList.forEach(apiController -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("### # ").append(apiController.getTagName()).append("\n")
                    .append("> desc: ").append(apiController.getTagDesc()).append("\n");
            AtomicInteger i = new AtomicInteger(1);
            apiController.getApiMethodList().forEach(apiMethod -> {
                stringBuilder.append("#### ").append(i).append(". ").append(apiMethod.getSummary()).append("\n")
                        .append("* path: `").append(apiMethod.getPath()).append("`\n")
                        .append("* method: `").append(apiMethod.getSupportMethods()).append("`\n")
                        .append("* consumes: `").append(apiMethod.getConsumers()).append("`\n")
                        .append("* parameters: ").append(genTable(JSON.parseArray(JSON.toJSONString(apiMethod.getParameters())))).append("\n")
                        .append("* produces: `").append(apiMethod.getProduces()).append("`\n")
                        .append("* response: ").append(apiMethod.getResponse()).append("\n");
                i.getAndIncrement();
            });
            writeToFile(file, stringBuilder);
        });
    }

    /**
     * 返回一个支持的RequestMethod
     *
     * @param jsonObject
     * @return
     */
    private static String methodName(JSONObject jsonObject) {
        String methodName = "";
        for (String s : jsonObject.keySet()) {
            methodName = s;
        }
        return methodName;
    }

    /**
     * 构建markdown格式的table
     *
     * @param data
     * @return
     */
    private static StringBuilder genTable(JSONArray data) {
        StringBuilder stringBuilder = new StringBuilder();
        if (data != null && data.size() > 0) {
            stringBuilder.append(
                    "\n\n    | name | type | description | required |\n    |:---:|:---:|:---:|:---:|\n");
            for (int i = 0; i < data.size(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                stringBuilder.append("    |").append(jsonObject.getString("name"))
                        .append("|").append(jsonObject.getString("type"))
                        .append("|").append(jsonObject.getString("desc"))
                        .append("|").append(jsonObject.getBoolean("required"))
                        .append("|\n");
            }
        } else {
            stringBuilder.append("无");
        }
        return stringBuilder;
    }

    /**
     * 将字符串写入文件中
     *
     * @param file          文件
     * @param stringBuilder 字符串
     */
    private static void writeToFile(File file, StringBuilder stringBuilder) {
        try {
            FileUtils.writeByteArrayToFile(file, stringBuilder.append("\n\n").toString().getBytes(StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成info信息
     */
    private static StringBuilder genInfo(ApiInfo apiInfo, File file) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("## # ").append(apiInfo.getTitle()).append("\n")
                .append("> ").append(apiInfo.getDescription()).append("\n")
                .append("* host: ").append("`").append(apiInfo.getHost()).append("`").append("\n")
                .append("* basePath: ").append("`").append(apiInfo.getBasePath()).append("`").append("\n")
                .append("* version: ").append(apiInfo.getVersion()).append("   (").append(DateTools.currentTimestampString()).append(")\n")
                .append("* termsOfService: ").append(apiInfo.getTermsOfService()).append("\n")
                .append("* contact: ").append(apiInfo.getContact()).append("\n")
                .append("* license: ").append(apiInfo.getLicense());
        writeToFile(file, stringBuilder);
        return stringBuilder;
    }
}
