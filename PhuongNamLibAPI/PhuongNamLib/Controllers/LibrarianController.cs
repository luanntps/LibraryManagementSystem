using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PhuongNamLib.Models;
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
        [Route("all-user")]
        public IActionResult GetAllUser()
        {
            List<Librarian> list = db.Librarians.ToList();
            return Ok(list);
        }
        [Authorize]
        [HttpPost]
        [Route("add-user")]
        public IActionResult AddUser(Librarian librarian)
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
        [Route("delete-user")]
        public IActionResult DeleteUser(Librarian librarian)
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
        [Route("update-user")]
        public IActionResult UpdateUser(Librarian librarian)
        {
         Librarian lib  = db.Librarians.Single(x => x.Id == librarian.Id);
         if(lib != null)
            {
                lib.Name = librarian.Name;
                lib.Password = librarian.Password;
                db.SaveChanges();
                return Ok(new Message("Okay"));
            }
         else
                return BadRequest();
        }
            
    }
}
