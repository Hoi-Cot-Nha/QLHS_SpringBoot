package Api;

import Model.TKB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TKBApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/tkb";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<TKB> getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<TKB>>(){}.getType();
        return gson.fromJson(response.body(), type);
    }

    public TKB getById(String maTKB) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + maTKB))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) return null;
        return gson.fromJson(response.body(), TKB.class);
    }

    public List<TKB> getByFilter(String maLop, String maMH, int thu) throws Exception {
        String url = BASE_URL + "/filter?maLop=" + URLEncoder.encode(maLop, StandardCharsets.UTF_8)
                + "&maMH=" + URLEncoder.encode(maMH, StandardCharsets.UTF_8)
                + "&thu=" + thu;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<TKB>>(){}.getType();
        return gson.fromJson(response.body(), type);
    }

    public List<String> getDistinctMaLop() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/danhsachlop"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(response.body(), type);
    }

    public TKB create(TKB tkb) throws Exception {
        String json = gson.toJson(tkb);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 400) throw new Exception("Trùng phòng hoặc trùng tiết");
        return gson.fromJson(response.body(), TKB.class);
    }

    public TKB update(String maTKB, TKB tkb) throws Exception {
        String json = gson.toJson(tkb);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + maTKB))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) throw new Exception("Không tìm thấy TKB");
        return gson.fromJson(response.body(), TKB.class);
    }

    public void delete(String maTKB) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + maTKB))
                .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) throw new Exception("Không tìm thấy TKB");
    }
}