/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package codes.biscuit.skyblockaddons.utils.pojo;

import com.google.gson.JsonObject;

public class SkyblockAddonsAPIResponse {
    private boolean success;
    private JsonObject response;

    public boolean isSuccess() {
        return this.success;
    }

    public JsonObject getResponse() {
        return this.response;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResponse(JsonObject response) {
        this.response = response;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SkyblockAddonsAPIResponse)) {
            return false;
        }
        SkyblockAddonsAPIResponse other = (SkyblockAddonsAPIResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isSuccess() != other.isSuccess()) {
            return false;
        }
        JsonObject this$response = this.getResponse();
        JsonObject other$response = other.getResponse();
        return !(this$response == null ? other$response != null : !this$response.equals(other$response));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SkyblockAddonsAPIResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        JsonObject $response = this.getResponse();
        result = result * 59 + ($response == null ? 43 : $response.hashCode());
        return result;
    }

    public String toString() {
        return "SkyblockAddonsAPIResponse(success=" + this.isSuccess() + ", response=" + this.getResponse() + ")";
    }
}

