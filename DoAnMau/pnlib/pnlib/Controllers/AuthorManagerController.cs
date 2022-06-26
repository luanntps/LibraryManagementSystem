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
    public class AuthorManagerController : ControllerBase
    {
        private readonly PNLibEntities db;
        public AuthorManagerController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-authormanager")]
        public IActionResult GetAllAuthorManager()
        {
            List<AuthorManager> list = db.AuthorManagers.ToList();
            return Ok(list);
        }
    }
}
