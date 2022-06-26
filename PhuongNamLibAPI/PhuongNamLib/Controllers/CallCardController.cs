using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

using PhuongNamLib.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace PhuongNamcal.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class CallCardController : ControllerBase
    {
        private readonly PNLibEntities db;
        public CallCardController(PNLibEntities context)
        {
            db = context;
        }
        [Route("all-callcard")]
        public IActionResult GetAllcallcard()
        {
            List<CallCard> list = db.CallCards.ToList();
            return Ok(list);
        }
        [Authorize]
        [HttpPost]
        [Route("add-callcard")]
        public IActionResult Addcallcard(CallCard CallCard)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.CallCards.Add(CallCard);
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
        [Route("delete-callcard")]
        public IActionResult Deletecallcard(CallCard CallCard)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest();
                }
                db.CallCards.Remove(CallCard);

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
        [Route("update-callcard")]
        public IActionResult Updatecallcard(CallCard CallCard)
        {
            CallCard cal = db.CallCards.Single(x => x.Id == CallCard.Id);
            if (cal != null)
            {
                cal.BeginDate = CallCard.BeginDate;
                cal.ExpiresDate = CallCard.ExpiresDate;
                db.SaveChanges();
                return Ok(new Message("Okay"));
            }
            else
                return BadRequest();
        }

    }
}

