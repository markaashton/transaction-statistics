package priv.mashton.n26.dtos;

public class TransactionRequest {

    private Double amount;
    private Long timestamp;

    public TransactionRequest() {
    }

    public TransactionRequest(Double amount, Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
