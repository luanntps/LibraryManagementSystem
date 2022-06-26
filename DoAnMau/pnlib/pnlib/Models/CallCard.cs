using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class CallCard
    {
        public int Id { get; set; }
        public DateTime BeginDate { get; set; }
        public DateTime ExpiresDate { get; set; }
        public int IdBook { get; set; }
        public int IdMember { get; set; }
        public string IdLibrarian { get; set; }

        public virtual Book IdBookNavigation { get; set; }
        public virtual Librarian IdLibrarianNavigation { get; set; }
        public virtual Member IdMemberNavigation { get; set; }
    }
}
