/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @auther Liam Marquis
 * @version    2019.03.31
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the daily access counts.
    private int[] dayCounts;
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        dayCounts = new int[32];
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }
    
    /**
     * Create an object to analyze hourly web accesses.
     * 
     * @param fileName The name of the log file that will be analyzed
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        dayCounts = new int[32];
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }
    
    /**
     * Finds the total number of accesses
     */
    public int numberOfAccesses(){
        int total = 0;
        
        do{
            total++;
            reader.next();
        }while(reader.hasNext());
        
        return total;
    }
    
    /**
     * Finds the busiest day
     * @return busiestDay The busiest day
     */
    public int busiestDay(){
        analyzeDailyData();
        int busiestDay = 0;
        int dailyData = 0;
        
        for (int day = 0; day < dayCounts.length; day++){
            if (dayCounts[day] > dailyData && day != 0){
                busiestDay = day;
                dailyData = dayCounts[day];
            }
        }
        return busiestDay;
    }
    
    /**
     * Finds the quietest day
     * @return quietestDay The quietest day
     */
    public int quietestDay(){
        analyzeDailyData();
        int quietestDay = 0;
        int dailyData = 100000;
        
        for (int day = 0; day < dayCounts.length; day++){
            if (dayCounts[day] < dailyData && day != 0){
                quietestDay = day;
                dailyData = dayCounts[day];
            }
        }
        return quietestDay;
    }
    
    /**
     * Finds the busiest hour
     * @return busiestHour The busiest hour
     */
    public int busiestHour(){
        analyzeHourlyData();
        int busiestHour = 0;
        int hourlyData = 0;
        
        for (int hour = 0; hour < hourCounts.length; hour++){
            if (hourCounts[hour] > hourlyData){
                busiestHour = hour;
                hourlyData = hourCounts[hour];
            }
        }
        return busiestHour;
    }
    
    /**
     * Finds the busiest two hour period
     * @return busiestHour The busiest two hour period
     */
    public int busiestTwoHour(){
        analyzeHourlyData();
        int busiestHour = 0;
        int hourlyData = 0;
        
        for (int hour = 0; hour < hourCounts.length; hour+=2){
            if (hourCounts[hour]+hourCounts[hour+1] > hourlyData){
                busiestHour = hour;
                hourlyData = hourCounts[hour];
            }
        }
        return busiestHour;
    }
    
    /**
     * Finds the quietest hour
     * @return quietestHour The quietest hour
     */
    public int quietestHour(){
        analyzeHourlyData();
        int busiestHour = 0;
        int hourlyData = 100000;
        
        for (int hour = 0; hour < hourCounts.length; hour++){
            if (hourCounts[hour] < hourlyData){
                busiestHour = hour;
                hourlyData = hourCounts[hour];
            }
        }
        return busiestHour;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Analyze the daily access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
