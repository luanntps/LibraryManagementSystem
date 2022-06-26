using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class Role
    {
        public Role()
        {
            Librarians = new HashSet<Librarian>();
        }

        public int Id { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Librarian> Librarians { get; set; }
    }
}
