using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PhuongNamLib.Models;
using System.Collections.Generic;
using System.Linq;

namespace PhuongNamLib.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class AdminController : ControllerBase
    {
        private readonly PNLibEntities db;
        public AdminController(PNLibEntities context)
        {
            db = context;
        }
        [HttpGet]
        [Route("all-admin")]
        public IActionResult GetAllUser()
        {
            List<Admin> list = db.Admins.ToList();
            return Ok(list);
        }
    }
}
