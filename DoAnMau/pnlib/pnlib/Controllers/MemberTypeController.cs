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
    public class MemberTypeController : ControllerBase
    {
        private readonly PNLibEntities db;
        public MemberTypeController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-membertype")]
        public IActionResult GetAllMembertype()
        {
            List<MemberType> list = db.MemberTypes.ToList();
            return Ok(list);

        }
    }
}
