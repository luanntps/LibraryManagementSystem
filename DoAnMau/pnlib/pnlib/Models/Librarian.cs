using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class Librarian
    {
        public Librarian()
        {
            CallCards = new HashSet<CallCard>();
        }

        public string Id { get; set; }
        public string Name { get; set; }
        public string Password { get; set; }
        public int IdRole { get; set; }

        public virtual Role IdRoleNavigation { get; set; }
        public virtual ICollection<CallCard> CallCards { get; set; }
    }
}
