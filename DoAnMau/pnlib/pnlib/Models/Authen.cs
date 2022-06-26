namespace pnlib.Models
{
    public class Authen
    {
        public string role { get; set; }
        public string token { get; set; }
        public Authen(string role, string token)
        {
            this.role = role;
            this.token = token;
        }
    }
}
