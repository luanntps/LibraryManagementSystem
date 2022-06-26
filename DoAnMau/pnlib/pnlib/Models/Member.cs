using System;
using System.Collections.Generic;

#nullable disable

namespace pnlib.Models
{
    public partial class Member
    {
        public Member()
        {
            CallCards = new HashSet<CallCard>();
        }

        public int Id { get; set; }
        public string Name { get; set; }
        public int CountTime { get; set; }
        public int IdMemberType { get; set; }

        public virtual MemberType IdMemberTypeNavigation { get; set; }
        public virtual ICollection<CallCard> CallCards { get; set; }
    }
}
