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
    public class LibrarianController : ControllerBase
    {
        private readonly PNLibEntities db;
        public LibrarianController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-librarian")]
        public IActionResult GetAllLibrarian()
        {
            List<Librarian> list = db.Librarians.ToList();
            return Ok(list);
        }
        [Authorize]
        [HttpPost]
        [Route("add-librarian")]
        public IActionResult AddLibrarian(Librarian librarian)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                  return  BadRequest();
                }
                db.Librarians.Add(librarian);
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
        [Route("delete-librarian")]
        public IActionResult DeleteLibrarian(Librarian librarian)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Librarians.Remove(librarian);

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
        [Route("update-librarian")]
        public IActionResult UpdateLibrarian(Librarian librarian)
        {
         Librarian lib  = db.Librarians.Single(x => x.Id.ToLower() == librarian.Id.ToLower());
         if(lib != null)
            {
                lib.Name = librarian.Name;
                lib.Password = librarian.Password;
                lib.IdRole = librarian.IdRole;
                db.SaveChanges();
                return Ok(new Message("Okay"));
            }
         else
                return BadRequest();
        }
            
    }
}
