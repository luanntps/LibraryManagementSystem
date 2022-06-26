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
    public class RoleController : ControllerBase
    {
        private readonly PNLibEntities db;
        public RoleController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-role")]
        public IActionResult GetAllRole()
        {
            List<Role> list = db.Roles.ToList();
            return Ok(list);
        }
    }
}
