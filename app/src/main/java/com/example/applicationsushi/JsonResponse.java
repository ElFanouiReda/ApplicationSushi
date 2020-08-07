package com.example.applicationsushi;

class JsonResponse {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String reponse) {
        this.response = reponse;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "reponse='" + response + '\'' +
                '}';
    }
}
