using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class MemberType
    {
        public MemberType()
        {
            Members = new HashSet<Member>();
        }

        public int Id { get; set; }
        public string Name { get; set; }
        public int DiscountPercents { get; set; }

        public virtual ICollection<Member> Members { get; set; }
    }
}
