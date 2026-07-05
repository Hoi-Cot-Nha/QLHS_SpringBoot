package Api;

import Model.ToBoMon;
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

public class ToHopMonApi {

    private static final String BASE_URL = ApiConfig.BASE_URL + "/api/tohopmon";

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    // Lấy tất cả
    public List<ToBoMon> getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = new TypeToken<List<ToBoMon>>() {}.getType();
        return gson.fromJson(response.body(), type);
    }

    // Lấy theo mã
    public ToBoMon getById(String maToHop) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + maToHop))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return null;
        }

        return gson.fromJson(response.body(), ToBoMon.class);
    }

    // Kiểm tra tồn tại
    public boolean exists(String maToHop) throws Exception {
        return getById(maToHop) != null;
    }

    // Thêm mới
    public ToBoMon create(ToBoMon toBoMon) throws Exception {

        String json = gson.toJson(toBoMon);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 409) {
            throw new Exception("Mã tổ hợp đã tồn tại");
        }

        if (response.statusCode() == 422) {
            throw new Exception("Tên tổ hợp đã tồn tại");
        }

        return gson.fromJson(response.body(), ToBoMon.class);
    }

    // Cập nhật
    public ToBoMon update(String maToHop, ToBoMon toBoMon) throws Exception {

        String json = gson.toJson(toBoMon);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + maToHop))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new Exception("Không tìm thấy tổ hợp môn");
        }

        return gson.fromJson(response.body(), ToBoMon.class);
    }

    // Xóa
    public void delete(String maToHop) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + maToHop))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new Exception("Không tìm thấy tổ hợp môn");
        }
    }

    // Tìm kiếm
    public List<ToBoMon> search(String keyword) throws Exception {

        String url = BASE_URL + "/search?keyword="
                + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = new TypeToken<List<ToBoMon>>() {}.getType();
        return gson.fromJson(response.body(), type);
    }
}