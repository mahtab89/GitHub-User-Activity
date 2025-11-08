import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main get <username>");
            return;
        }
        String username = args[1];
        String url = String.format("https://api.github.com/users/%s/events", username);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Could not fetch! Status: " + response.statusCode());
                return;
            }
            if (response.statusCode() == 404) {
                System.out.println("User does not exist! Status: " + response.statusCode());
                return;
            }

            JSONArray events = new JSONArray(response.body());
            if (events.isEmpty()) {
                System.out.println("No recent activity found for user: " + username);
                return;
            }
            System.out.println("Output: ");
            HashSet<String> seen = new HashSet<>();

            for (int i = 0; i < events.length(); i++) {

                JSONObject event = events.getJSONObject(i);
                String type = event.getString("type");
                String repoName = event.getJSONObject("repo").getString("name");

                String uniqueKey = type + "_" + repoName;
                if (seen.contains(uniqueKey)) continue;
                seen.add(uniqueKey);

                switch (type) {
                    case "PushEvent" -> {
                        JSONObject payload = event.getJSONObject("payload");
                        int commits = payload.has("commits") ? payload.getJSONArray("commits").length() : 1;
                        System.out.println("- Pushed " + commits + " commit" + (commits > 1 ? "s" : "") + " to " + repoName);
                    }
                    case "CreateEvent" -> {
                        JSONObject payload = event.getJSONObject("payload");
                        String refType = payload.optString("ref_type", "repository");
                        String ref = payload.optString("ref", "");

                        if (refType.equals("branch")) {
                            System.out.println("- Created a new branch '" + ref + "' in " + repoName);
                        } else if (refType.equals("repository")) {
                            System.out.println("- Created a new repository " + repoName);
                        } else {
                            System.out.println("- Created a new " + refType + " in " + repoName);
                        }
                    }
                    case "WatchEvent" ->  System.out.println("- Starred " + repoName);
                    case "ForkEvent" -> System.out.println("- Forked " + repoName);
                    case "IssuesEvent" -> {
                        JSONObject payload = event.getJSONObject("payload");
                        String action = payload.optString("action", "updated");
                        System.out.println("- " + capitalize(action) + " an issue in " + repoName);
                    }
                    default -> System.out.println("- " + type.replace("Event", "") + " on " + repoName);
                }

            }

        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }


    }

    private static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
