package responses;

public class CreateOrderResponse {
    private final Integer id;

    public CreateOrderResponse(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
