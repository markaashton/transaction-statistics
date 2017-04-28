package priv.mashton.n26.model;

public class TransactionStatistics {

    private double sum = 0.0;
    private double avg = 0.0;
    private double max = 0.0;
    private double min = 0.0;
    private long count = 0L;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public TransactionStatistics makeCopy() {
        TransactionStatistics defensiveCopy = new TransactionStatistics();
        defensiveCopy.setSum(sum);
        defensiveCopy.setMin(min);
        defensiveCopy.setMax(max);
        defensiveCopy.setAvg(avg);
        defensiveCopy.setCount(count);
        return defensiveCopy;
    }
}
