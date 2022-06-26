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
    public class KindController : ControllerBase
    {
        private readonly PNLibEntities db;
        public KindController(PNLibEntities context)
        {
            db = context;
        }
        [Route("all-kind")]
        public IActionResult GetAllKind()
        {
            List<Kind> list = db.Kinds.ToList();
            return Ok(list);
        }
        [Authorize]
        [HttpPost]
        [Route("add-kind")]
        public IActionResult AddKind(Kind Kind)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Kinds.Add(Kind);
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
        [Route("delete-kind")]
        public IActionResult DeleteKind(Kind Kind)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Kinds.Remove(Kind);

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
        [Route("update-kind")]
        public IActionResult UpdateKind(Kind Kind)
        {
            Kind kind = db.Kinds.Single(x => x.Id == Kind.Id);
            if (kind != null)
            {
                kind.Name = Kind.Name;
                db.SaveChanges();
                return Ok(new Message("Okay"));
            }
            else
                return BadRequest();
        }

    }
}
