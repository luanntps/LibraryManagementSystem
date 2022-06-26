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
    public class LanguageController : ControllerBase
    {
        private readonly PNLibEntities db;
        public LanguageController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize]
        [HttpGet]
        [Route("all-language")]
        public IActionResult GetAllLanguage()
        {
            List<Language> list = db.Languages.ToList();
            return Ok(list);
        }
    }
}
