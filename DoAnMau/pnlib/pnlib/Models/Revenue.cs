namespace pnlib.Models
{
    public class Revenue
    {
        public int date { get; set; }  
        public int revenue { get; set; }    
        public Revenue(int date, int revenue)
        {
            this.date = date;  
            this.revenue = revenue;  
        }
    }
}
