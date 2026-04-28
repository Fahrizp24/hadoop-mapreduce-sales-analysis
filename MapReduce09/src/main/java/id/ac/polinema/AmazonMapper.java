package id.ac.polinema;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AmazonMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    private final static DoubleWritable amount = new DoubleWritable();
    private Text category = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        // Split aman untuk CSV yang memiliki tanda kutip
        String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        // Ubah angka di dalam kolom
        if (columns.length > 15) { // Pastikan kolom minimal sampai index 15
            try {
                // Index 9 untuk Category
                String catName = columns[9].replaceAll("\"", "").trim();

                // Index 15 untuk Amount
                String amountStr = columns[15].replaceAll("\"", "").trim();

                if (!catName.isEmpty() && !amountStr.isEmpty()) {
                    double price = Double.parseDouble(amountStr);
                    category.set(catName);
                    amount.set(price);
                    context.write(category, amount);
                }
            } catch (Exception e) {
                // Skip baris yang error parsing
            }
        }
    }
}