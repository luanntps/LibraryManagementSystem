using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace pnlib.Models
{
    public partial class PNLibEntities : DbContext
    {
      

        public PNLibEntities(DbContextOptions<PNLibEntities> options)
            : base(options)
        {
        }

        public virtual DbSet<Author> Authors { get; set; }
        public virtual DbSet<AuthorManager> AuthorManagers { get; set; }
        public virtual DbSet<Book> Books { get; set; }
        public virtual DbSet<CallCard> CallCards { get; set; }
        public virtual DbSet<Kind> Kinds { get; set; }
        public virtual DbSet<Language> Languages { get; set; }
        public virtual DbSet<Librarian> Librarians { get; set; }
        public virtual DbSet<Member> Members { get; set; }
        public virtual DbSet<MemberType> MemberTypes { get; set; }
        public virtual DbSet<Publisher> Publishers { get; set; }
        public virtual DbSet<Role> Roles { get; set; }

      

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasDefaultSchema("luanntpss")
                .HasAnnotation("Relational:Collation", "SQL_Latin1_General_CP1_CI_AS");

            modelBuilder.Entity<Author>(entity =>
            {
                entity.ToTable("Author");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Dob)
                    .HasColumnType("date")
                    .HasColumnName("DOB");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<AuthorManager>(entity =>
            {
                entity.HasKey(e => new { e.IdAuthor, e.IdBook })
                    .HasName("PK__AuthorMa__1E1BE388297B9747");

                entity.ToTable("AuthorManager");

                entity.Property(e => e.IdAuthor)
                    .ValueGeneratedOnAdd()
                    .HasColumnName("ID_Author");

                entity.Property(e => e.IdBook).HasColumnName("ID_Book");

                entity.HasOne(d => d.IdAuthorNavigation)
                    .WithMany(p => p.AuthorManagers)
                    .HasForeignKey(d => d.IdAuthor)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__AuthorMan__ID_Au__4CA06362");

                entity.HasOne(d => d.IdBookNavigation)
                    .WithMany(p => p.AuthorManagers)
                    .HasForeignKey(d => d.IdBook)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__AuthorMan__ID_Bo__4D94879B");
            });

            modelBuilder.Entity<Book>(entity =>
            {
                entity.ToTable("Book");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.IdKind).HasColumnName("ID_Kind");

                entity.Property(e => e.IdLanguage).HasColumnName("ID_Language");

                entity.Property(e => e.IdPublisher).HasColumnName("ID_Publisher");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.HasOne(d => d.IdKindNavigation)
                    .WithMany(p => p.Books)
                    .HasForeignKey(d => d.IdKind)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Book__ID_Kind__4222D4EF");

                entity.HasOne(d => d.IdLanguageNavigation)
                    .WithMany(p => p.Books)
                    .HasForeignKey(d => d.IdLanguage)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Book__ID_Languag__440B1D61");

                entity.HasOne(d => d.IdPublisherNavigation)
                    .WithMany(p => p.Books)
                    .HasForeignKey(d => d.IdPublisher)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Book__ID_Publish__4316F928");
            });

            modelBuilder.Entity<CallCard>(entity =>
            {
                entity.ToTable("CallCard");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.BeginDate).HasColumnType("date");

                entity.Property(e => e.ExpiresDate).HasColumnType("date");

                entity.Property(e => e.IdBook).HasColumnName("ID_Book");

                entity.Property(e => e.IdLibrarian)
                    .IsRequired()
                    .HasMaxLength(255)
                    .HasColumnName("ID_Librarian");

                entity.Property(e => e.IdMember).HasColumnName("ID_Member");

                entity.HasOne(d => d.IdBookNavigation)
                    .WithMany(p => p.CallCards)
                    .HasForeignKey(d => d.IdBook)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__CallCard__ID_Boo__5070F446");

                entity.HasOne(d => d.IdLibrarianNavigation)
                    .WithMany(p => p.CallCards)
                    .HasForeignKey(d => d.IdLibrarian)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__CallCard__ID_Lib__52593CB8");

                entity.HasOne(d => d.IdMemberNavigation)
                    .WithMany(p => p.CallCards)
                    .HasForeignKey(d => d.IdMember)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__CallCard__ID_Mem__5165187F");
            });

            modelBuilder.Entity<Kind>(entity =>
            {
                entity.ToTable("Kind");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<Language>(entity =>
            {
                entity.ToTable("Language");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<Librarian>(entity =>
            {
                entity.ToTable("Librarian");

                entity.Property(e => e.Id)
                    .HasMaxLength(255)
                    .HasColumnName("ID");

                entity.Property(e => e.IdRole).HasColumnName("ID_Role");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.HasOne(d => d.IdRoleNavigation)
                    .WithMany(p => p.Librarians)
                    .HasForeignKey(d => d.IdRole)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Librarian__ID_Ro__49C3F6B7");
            });

            modelBuilder.Entity<Member>(entity =>
            {
                entity.ToTable("Member");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.IdMemberType).HasColumnName("ID_MemberType");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.HasOne(d => d.IdMemberTypeNavigation)
                    .WithMany(p => p.Members)
                    .HasForeignKey(d => d.IdMemberType)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Member__ID_Membe__46E78A0C");
            });

            modelBuilder.Entity<MemberType>(entity =>
            {
                entity.ToTable("MemberType");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.DiscountPercents).HasColumnName("Discount_percents");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<Publisher>(entity =>
            {
                entity.ToTable("Publisher");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Location)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<Role>(entity =>
            {
                entity.ToTable("Role");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
