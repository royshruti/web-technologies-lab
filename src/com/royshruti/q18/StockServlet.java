package com.royshruti.q18;

import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = {
        "/q18/getStocks" }, description = "A servlet to return stock values using SSE. Append stock names as query parameters using 'stocks=<stockname>&stocks=<stockname>...")
public class StockServlet extends HttpServlet {

    /**
     * A class which sends stock prices to the frontend user
     * identified by the response object.
     */
    class StockSender implements Runnable {
        /**
         * Stores the number of times the frontend user gets the stock price.
         */
        int count;

        /**
         * The name of the stock for which the user needs to get updated.
         */
        String stockName;

        HttpServletResponse response;

        Object lock;

        public StockSender(String stockName, int count, HttpServletResponse response, Object lock) {
            this.stockName = stockName;
            this.count = count;
            this.response = response;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                PrintWriter pw = this.response.getWriter();

                for (int i = 0; i < this.count; i++) {

                    // in an actual application, you'll get this data from a message
                    // queue or atleast some real time database.
                    double price = Math.random() * 1001;

                    // creates the data string which has to be returned to the user
                    String data = String.format("%d -> %s %.2f", i + 1, this.stockName, price);

                    synchronized (this.lock) {

                        // take note of the 2 \n 's, also on the next line.
                        pw.write("event: server-time\n\n");

                        // adds the data string to the response
                        pw.write("data: " + data + "\n\n");

                        // clears the buffer early, otherwise it will send the complete
                        // information when writer is closed at the end of all threads.
                        this.response.flushBuffer();

                    }

                    // generate a random time to wait before sending price again
                    int backoffTime = (int) (1000 * Math.random()) + 1001;

                    // do not sleep after sending the last data
                    if (i != this.count - 1) {
                        // sleeps for that amount of time
                        Thread.sleep(backoffTime);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String[] stocks = request.getParameterValues("stocks");
        Thread[] threads = new Thread[stocks.length];
        Object lock = new Object();

        try {
            response.setContentType("text/event-stream");
            System.out.println(Arrays.toString(stocks));
            for (int i = 0; i < stocks.length; i++) {
                System.out.println("Individual Stock Name :: " + stocks[i]);
                threads[i] = new Thread(new StockSender(stocks[i], 10, response, lock));
                threads[i].start();
            }

            for (int i = 0; i < stocks.length; i++) {
                threads[i].join();
            }

            response.getWriter().close();
        } catch (Exception e) {
            System.out.println("Error message : " + e.getMessage());
        }
    }
}
