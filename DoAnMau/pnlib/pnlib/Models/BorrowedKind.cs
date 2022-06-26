namespace pnlib.Models
{
    public class BorrowedKind
    {

        public string name { get; set; }    
        public int count { get; set; }
        public BorrowedKind(string name, int count)
        {
            this.name = name;   
            this.count = count;
        }
    }
}
