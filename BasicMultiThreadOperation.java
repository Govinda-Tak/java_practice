
class FileDownloader extends Thread {
    private String fileName;

    public FileDownloader(String fileName) {
        this.fileName = fileName;
        this.setName("FileDownloader-" + fileName);
    }

    @Override
    public void run() {
        System.out.println(getName() + " started downloading...");
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + ": Downloading " + i * 20 + "% of " + fileName);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted!");
            }
        }
        System.out.println(getName() + " completed download.");
    }
}



class DataProcessor implements Runnable {
    private String dataType;

    public DataProcessor(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " started processing " + dataType + " data.");
        for (int i = 1; i <= 5; i++) {
            System.out.println(threadName + ": Processing batch " + i + " of " + dataType);
            try {
                Thread.sleep(400); // Simulate processing delay
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted!");
            }
        }
        System.out.println(threadName + " finished processing " + dataType + " data.");
    }
}



public class MultiThreadDemo {
    public static void main(String[] args) {
        System.out.println("=== Multi-Threading Demo ===");

    
        FileDownloader thread1 = new FileDownloader("video.mp4");
        FileDownloader thread2 = new FileDownloader("ebook.pdf");

   
        DataProcessor task1 = new DataProcessor("JSON");
        DataProcessor task2 = new DataProcessor("CSV");

        Thread thread3 = new Thread(task1, "DataProcessor-JSON");
        Thread thread4 = new Thread(task2, "DataProcessor-CSV");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

  
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted!");
        }

        System.out.println("\n=== All threads completed successfully ===");
    }
}
