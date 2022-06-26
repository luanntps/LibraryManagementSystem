using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class Book
    {
        public Book()
        {
            AuthorManagers = new HashSet<AuthorManager>();
            CallCards = new HashSet<CallCard>();
        }

        public int Id { get; set; }
        public string Name { get; set; }
        public int Price { get; set; }
        public int CopiesQuantity { get; set; }
        public int IdKind { get; set; }
        public int IdPublisher { get; set; }
        public int IdLanguage { get; set; }

        public virtual Kind IdKindNavigation { get; set; }
        public virtual Language IdLanguageNavigation { get; set; }
        public virtual Publisher IdPublisherNavigation { get; set; }
        public virtual ICollection<AuthorManager> AuthorManagers { get; set; }
        public virtual ICollection<CallCard> CallCards { get; set; }
    }
}
