namespace pnlib.Models
{
    public class TopTenBook
    {
        public string name { get; set; }
        public int count { get; set; }
        public string kind { get; set; }
        public TopTenBook(string name, string kind ,int count)
        {
            this.name = name;
            this.kind = kind;
            this.count = count;
        }
    }
}
