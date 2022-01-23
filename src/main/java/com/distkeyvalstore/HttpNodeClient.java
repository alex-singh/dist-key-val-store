package com.distkeyvalstore;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HttpNodeClient {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .executor(executorService)
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    public void putRequests(List<String> addresses, String value, String key){
        List<URI> URIs = addresses.stream()
                        .map(a -> URI.create(a + "/private/values/" + key)).collect(Collectors.toList());

        List<CompletableFuture<String>> result = URIs.stream()
                .map(url -> httpClient.sendAsync(
                                HttpRequest.newBuilder(url)
                                        .PUT(HttpRequest.BodyPublishers.ofString(value))
                                        .build(),
                                HttpResponse.BodyHandlers.ofString())
                        .thenApply(response -> response.body()))
                .collect(Collectors.toList());

        for (CompletableFuture<String> future : result) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                // swallow error
            } catch (ExecutionException e) {
                // swallow error
            }
        }
    }
}
