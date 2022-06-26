using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class Author
    {
        public Author()
        {
            AuthorManagers = new HashSet<AuthorManager>();
        }

        public int Id { get; set; }
        public string Name { get; set; }
        public DateTime Dob { get; set; }

        public virtual ICollection<AuthorManager> AuthorManagers { get; set; }
    }
}
