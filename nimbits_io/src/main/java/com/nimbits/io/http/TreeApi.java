package com.nimbits.io.http;

import com.nimbits.client.model.entity.Entity;
import retrofit.http.GET;

import java.util.List;

public interface TreeApi {
    final String TREE_API = "/service/v2/tree";

    @GET(TREE_API)
    List<Entity> getTree();
}
