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
    public class PublisherController : ControllerBase
    {
        private readonly PNLibEntities db;
        public PublisherController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-publisher")]
        public IActionResult GetAllPublisher()
        {
            List<Publisher> list = db.Publishers.ToList();
            return Ok(list);
        }
    }
}
