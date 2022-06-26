using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class AuthorManager
    {
        public int IdAuthor { get; set; }
        public int IdBook { get; set; }

        public virtual Author IdAuthorNavigation { get; set; }
        public virtual Book IdBookNavigation { get; set; }
    }
}
