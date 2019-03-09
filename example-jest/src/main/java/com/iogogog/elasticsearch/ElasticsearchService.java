package com.iogogog.elasticsearch;

import io.searchbox.action.Action;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * Created by tao.zeng on 2019-03-08.
 */
public interface ElasticsearchService {

    /**
     * @param index
     * @return
     * @throws IOException
     */
    JestResult createIndex(String index) throws IOException;

    /**
     * Settings.Builder builder = Settings.builder();
     * builder.put("number_of_shards",5);
     * builder.put("number_of_replicas",1);
     *
     * @param index
     * @param builder
     * @return
     * @throws IOException
     */
    JestResult createIndex(String index, Settings.Builder builder) throws IOException;

    void createIndexAsync(String index, ActionListener actionListener);

    /**
     * Settings.Builder builder = Settings.builder();
     * builder.put("number_of_shards",5);
     * builder.put("number_of_replicas",1);
     *
     * @param index
     * @param builder
     * @param actionListener
     */
    void createIndexAsync(String index, Settings.Builder builder, ActionListener actionListener);

    JestResult indicesExists(String index) throws IOException;

    void indicesExistsAsync(String index, ActionListener actionListener);

    JestResult createMapping(String index, String type, Map<String, Map<String, Object>> mapping) throws IOException;

    void createMappingAsync(String index, String type, Map<String, Map<String, Object>> mapping, ActionListener actionListener);

    String getMapping(String index, String type) throws IOException;


    JestResult deleteIndex(String index) throws IOException;

    void deleteIndexAsync(String index, ActionListener actionListener);


    JestResult deleteById(String index, String type, String id) throws IOException;

    void deleteByIdAsync(String index, String type, String id, ActionListener actionListener);


    JestResult save(String index, String type, String id, Object data) throws IOException;

    void saveAsync(String index, String type, String id, Object data, ActionListener actionListener);


    JestResult batchSave(String index, String type, String id, Collection<?> data) throws IOException;

    void batchSaveAsync(String index, String type, String id, Collection<?> data, ActionListener actionListener);


    default Action<JestResult> buildCreateIndexAction(String index, Settings.Builder builder) {
        CreateIndex.Builder build = new CreateIndex.Builder(index);
        if (builder != null) {
            build.settings(builder.build().getAsMap());
        }
        return build.build();
    }

    default PutMapping buildCreateMapping(String index, String type, Map<String, Map<String, Object>> mapping) {
        return new PutMapping.Builder(index, type, mapping).build();
    }


    default DeleteIndex buildDeleteIndex(String index) {
        return new DeleteIndex.Builder(index).build();
    }

    default Delete buildDeleteById(String index, String type, String id) {
        return new Delete.Builder(id)
                .index(index)
                .type(type)
                .build();
    }

    default Index buildItemSave(String index, String type, String id, Object data) {
        Index.Builder builder = new Index.Builder(data)
                .index(index)
                .type(type)
                .refresh(true);

        if (StringUtils.isNotEmpty(id)) {
            builder.id(id);
        }

        return builder.build();
    }

    default Bulk buildBatchSave(String index, String type, String id, Collection<?> data) {
        Bulk.Builder builder = new Bulk.Builder();
        data.forEach(x -> builder.addAction(buildItemSave(index, type, id, x)));
        return builder.build();
    }

    default IndicesExists buildIndicesExists(String index) {
        return new IndicesExists.Builder(index).build();
    }

    /**
     * Created by tao.zeng on 2019-03-08.
     */
    interface ActionListener {

        void onCompleted(String action, JestResult result);

        void onFailure(String action, Exception e);
    }


    /**
     * Created by tao.zeng on 2019-03-08.
     */
    interface AsyncAction {

        String ACTION_CREATE_INDEX_ASYNC = "create_index_async";

        String ACTION_CREATE_MAPPING_ASYNC = "create_mapping_async";

        String ACTION_DELETE_INDEX_ASYNC = "delete_index_async";

        String ACTION_DELETE_BY_ID_ASYNC = "delete_by_id_async";

        String ACTION_SAVE_ASYNC = "save_async";

        String ACTION_BATCH_SAVE_ASYNC = "batch_save_async";

        String ACTION_INDICES_EXISTS = "action_indices_exists";
    }
}
