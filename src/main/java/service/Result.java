package service;

/**
 * Result<T>
 * Generic wrapper for operation results, providing success/failure status and error handling.
 *
 * Features:
 * - Type-safe wrapper for operation results
 * - Error message handling
 * - Success status checking
 *
 * Usage:
 * Result<User> result = userService.login(email, password);
 * if (result.isSuccess()) {
 *     User user = result.getData();
 * } else {
 *     String error = result.getError();
 * }
 */
public class Result<T> {
    private final T data;
    private final String error;
    private final boolean success;

    private Result(T data, String error, boolean success) {
        this.data = data;
        this.error = error;
        this.success = success;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, true);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error, false);
    }

    public T getData() {
        return data; }
    public String getError() {
        return error; }
    public boolean isSuccess() {
        return success; }
}
