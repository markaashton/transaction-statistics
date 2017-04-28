package priv.mashton.n26.statistics;

import priv.mashton.n26.model.Transaction;
import priv.mashton.n26.model.TransactionStatistics;

import java.util.Iterator;
import java.util.List;

final class StatisticsUtils {

    static TransactionStatistics calculateStatistics(List<Transaction> transactions) {

        if (transactions == null || transactions.isEmpty()) {
            return new TransactionStatistics();
        }

        Iterator<Transaction> iterator = transactions.iterator();

        Transaction tx = iterator.next();
        double min = tx.getAmount();
        double sum = tx.getAmount();
        double max = tx.getAmount();
        long count = transactions.size();

        while (iterator.hasNext()) {
            tx = iterator.next();

            double amount = tx.getAmount();
            sum += amount;

            if (amount < min) {
                min = amount;
            }

            if (amount > max) {
                max = amount;
            }
        }

        double avgBeforeRounding = sum / count;
        double avg = Math.round(avgBeforeRounding * 100.0) / 100.0;

        TransactionStatistics stats = new TransactionStatistics();
        stats.setAvg(avg);
        stats.setCount(count);
        stats.setMax(max);
        stats.setMin(min);
        stats.setSum(sum);

        return stats;

    }

}
