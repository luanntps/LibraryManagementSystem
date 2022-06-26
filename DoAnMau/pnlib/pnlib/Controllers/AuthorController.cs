using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using pnlib.Models;
using System.Collections.Generic;
using System.Linq;

namespace pnlib.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class AuthorController : ControllerBase
    {
        private readonly PNLibEntities db;
        public AuthorController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-author")]
        public IActionResult GetAllAuthor()
        {
            List<Author> list = db.Authors.ToList();
            return Ok(list);
        }
    }
}
