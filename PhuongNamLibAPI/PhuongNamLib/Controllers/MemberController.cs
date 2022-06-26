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
    public class MemberController : ControllerBase
    {
        private readonly PNLibEntities db;
        public MemberController(PNLibEntities context)
        {
            db = context;
        }
        [Route("all-member")]
        public IActionResult GetAllMember()
        {
            List<Member> list = db.Members.ToList();
            return Ok(list);
        }
        [Authorize]
        [HttpPost]
        [Route("add-member")]
        public IActionResult AddMember(Member Member)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Members.Add(Member);
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
        [Route("delete-member")]
        public IActionResult DeleteMember(Member Member)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.Members.Remove(Member);

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
        [Route("update-member")]
        public IActionResult UpdateMember(Member Member)
        {
            Member mem = db.Members.Single(x => x.Id == Member.Id);
            if (mem != null)
            {
                mem.Name = Member.Name;
                mem.CountTime = Member.CountTime;
                db.SaveChanges();
                return Ok(new Message("Okay"));
            }
            else
                return BadRequest();
        }

    }
}
