package com.iogogog.elasticsearch.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.iogogog.elasticsearch.ElasticsearchService;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.core.BulkResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.indices.mapping.GetMapping;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by tao.zeng on 2019-03-08.
 */
@Slf4j
@Component
public class ElasticsearchServiceImpl implements ElasticsearchService, ElasticsearchService.AsyncAction {

    @Autowired
    private JestClient jestClient;

    @Override
    public JestResult createIndex(String index) throws IOException {
        return createIndex(index, null);
    }

    @Override
    public JestResult createIndex(String index, Settings.Builder builder) throws IOException {
        Action<JestResult> action = buildCreateIndexAction(index, builder);
        return jestClient.execute(action);
    }

    @Override
    public void createIndexAsync(String index, ActionListener actionListener) {
        createIndexAsync(index, null, actionListener);
    }

    @Override
    public void createIndexAsync(String index, Settings.Builder builder, ActionListener actionListener) {
        Action<JestResult> action = buildCreateIndexAction(index, builder);
        jestClient.executeAsync(action, new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult jestResult) {
                if (actionListener != null) {
                    actionListener.onSuccessful(ACTION_CREATE_INDEX_ASYNC, jestResult);
                } else {
                    log.debug("创建index:{} 结果: {}  errorMessage: {}", index, jestResult.isSucceeded(), jestResult.getErrorMessage());
                }
            }

            @Override
            public void failed(Exception e) {
                if (actionListener != null) {
                    actionListener.onFailure(ACTION_CREATE_INDEX_ASYNC, e);
                } else {
                    log.error("创建index:{} 异常. {}", index, e);
                }
            }
        });
    }

    @Override
    public JestResult createMapping(String index, String type, JSONObject mapping) throws IOException {
        return jestClient.execute(buildCreateMapping(index, type, mapping));
    }

    @Override
    public void createMappingAsync(String index, String type, JSONObject mapping, ActionListener actionListener) {
        jestClient.executeAsync(buildCreateMapping(index, type, mapping), new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult jestResult) {
                if (actionListener != null) {
                    actionListener.onSuccessful(ACTION_CREATE_MAPPING_ASYNC, jestResult);
                } else {
                    log.debug("createMappingAsync:{} 结果: {}  errorMessage: {}", index, jestResult.isSucceeded(), jestResult.getErrorMessage());
                }
            }

            @Override
            public void failed(Exception e) {
                if (actionListener != null) {
                    actionListener.onFailure(ACTION_CREATE_MAPPING_ASYNC, e);
                } else {
                    log.error("createMappingAsync:{} 异常. {}", index, e);
                }
            }
        });
    }


    @Override
    public String getMapping(String index, String type) throws IOException {
        GetMapping mapping = new GetMapping.Builder().addIndex(index).addType(type).build();
        JestResult result = jestClient.execute(mapping);
        return result.getSourceAsObject(JsonObject.class).toString();
    }


    @Override
    public JestResult deleteIndex(String index) throws IOException {
        return jestClient.execute(buildDeleteIndex(index));
    }

    @Override
    public void deleteIndexAsync(String index, ActionListener actionListener) {
        jestClient.executeAsync(buildDeleteIndex(index), new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult jestResult) {
                if (actionListener != null) {
                    actionListener.onSuccessful(ACTION_DELETE_INDEX_ASYNC, jestResult);
                } else {
                    log.debug("deleteIndexAsync:{} 结果: {}  errorMessage: {}", index, jestResult.isSucceeded(), jestResult.getErrorMessage());
                }
            }

            @Override
            public void failed(Exception e) {
                if (actionListener != null) {
                    actionListener.onFailure(ACTION_DELETE_INDEX_ASYNC, e);
                } else {
                    log.error("deleteIndexAsync:{} 异常. {}", index, e);
                }
            }
        });
    }


    @Override
    public JestResult deleteById(String index, String type, String id) throws IOException {
        return jestClient.execute(buildDeleteById(index, type, id));
    }


    @Override
    public void deleteByIdAsync(String index, String type, String id, ActionListener actionListener) {
        jestClient.executeAsync(buildDeleteById(index, type, id), new JestResultHandler<DocumentResult>() {
            @Override
            public void completed(DocumentResult documentResult) {
                if (actionListener != null) {
                    actionListener.onSuccessful(ACTION_DELETE_BY_ID_ASYNC, documentResult);
                } else {
                    log.debug("deleteByIdAsync:{} 结果: {}  errorMessage: {}", index, documentResult.isSucceeded(), documentResult.getErrorMessage());
                }
            }

            @Override
            public void failed(Exception e) {
                if (actionListener != null) {
                    actionListener.onFailure(ACTION_DELETE_BY_ID_ASYNC, e);
                } else {
                    log.error("deleteByIdAsync:{} 异常. {}", index, e);
                }
            }
        });
    }

    @Override
    public JestResult save(String index, String type, String id, Object data) throws IOException {
        return jestClient.execute(buildSave(index, type, id, data));
    }

    @Override
    public void saveAsync(String index, String type, String id, Object data, ActionListener actionListener) {
        jestClient.executeAsync(buildSave(index, type, id, data), new JestResultHandler<DocumentResult>() {
            @Override
            public void completed(DocumentResult documentResult) {
                if (actionListener != null) {
                    actionListener.onSuccessful(ACTION_SAVE_ASYNC, documentResult);
                } else {
                    log.debug("saveAsync:{} 结果: {}  errorMessage: {}", index, documentResult.isSucceeded(), documentResult.getErrorMessage());
                }
            }

            @Override
            public void failed(Exception e) {
                if (actionListener != null) {
                    actionListener.onFailure(ACTION_SAVE_ASYNC, e);
                } else {
                    log.error("saveAsync:{} 异常. {}", index, e);
                }
            }
        });
    }


    @Override
    public JestResult batchSave(String index, String type, String id, List<?> data) throws IOException {
        return jestClient.execute(buildBatchSave(index, type, id, data));
    }

    @Override
    public void batchSaveAsync(String index, String type, String id, List<?> data, ActionListener actionListener) {
        jestClient.executeAsync(buildBatchSave(index, type, id, data), new JestResultHandler<BulkResult>() {
            @Override
            public void completed(BulkResult bulkResult) {
                if (actionListener != null) {
                    actionListener.onSuccessful(ACTION_BATCH_SAVE_ASYNC, bulkResult);
                } else {
                    log.debug("batchSaveAsync:{} 结果: {}  errorMessage: {}", index, bulkResult.isSucceeded(), bulkResult.getErrorMessage());
                }
            }

            @Override
            public void failed(Exception e) {
                if (actionListener != null) {
                    actionListener.onFailure(ACTION_BATCH_SAVE_ASYNC, e);
                } else {
                    log.error("batchSaveAsync:{} 异常. {}", index, e);
                }
            }
        });
    }
}
