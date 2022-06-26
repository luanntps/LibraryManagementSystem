using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using pnlib.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace PhuongNamLib.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class BookController : ControllerBase
    {
        private readonly PNLibEntities db;
        public BookController(PNLibEntities context)
        {
            db = context;
        }
        [Route("all-book")]
        public IActionResult GetAllBook()
        {
            List<Book> list = db.Books.ToList();
            return Ok(list);
        }
        [Authorize]
        [HttpPost]
        [Route("add-book")]
        public IActionResult AddBook(Book Book)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Books.Add(Book);
                db.SaveChanges();
            }
            catch (Exception)
            {
                return BadRequest();
            }
            return Ok(new Message("Okay"));
        }
        [Authorize]
        [HttpPost]
        [Route("delete-book")]
        public IActionResult DeleteBook(Book Book)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Books.Remove(Book);

                db.SaveChanges();
            }
            catch (Exception)
            {
                return BadRequest();
            }
            return Ok(new Message("Okay"));
        }
        [Authorize]
        [HttpPost]
        [Route("update-book")]
        public IActionResult UpdateBook(Book Book)
        {
            Book book = db.Books.Single(x => x.Id == Book.Id);
            if (book != null)
            {
                book.Name = Book.Name;
                book.IdKind = Book.IdKind;
                book.Price = Book.Price;
                book.IdPublisher = Book.IdPublisher;
                book.IdLanguage = Book.IdLanguage;
                db.SaveChanges();
                return Ok(new Message("Okay"));
            }
            else
                return BadRequest();
        }

    }
}
