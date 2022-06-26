using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class Kind
    {
        public Kind()
        {
            Books = new HashSet<Book>();
        }

        public int Id { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Book> Books { get; set; }
    }
}
