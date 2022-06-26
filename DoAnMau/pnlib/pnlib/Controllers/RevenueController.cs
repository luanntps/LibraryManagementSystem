using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using pnlib.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace pnlib.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class RevenueController : ControllerBase
    {
        private readonly PNLibEntities db;
        public RevenueController(PNLibEntities context)
        {
            db = context;
        }
        [Authorize ]
        [HttpGet]
        [Route("revenue-by-month")]
        public IActionResult GetAllRevenueByMonth()
        {
            List<Revenue> revenueList = new List<Revenue>();

            int revenue = 0;
            for (int i = 1; i <= 12; i++)
            {
                var kq = db.CallCards.Join(db.Books, c => c.IdBook, b => b.Id, (c, b) => new
                {
                    Revenue = b.Price,
                    BeginDate = c.BeginDate
                })
                .Where(d => d.BeginDate.Month == i && d.BeginDate.Year == DateTime.Now.Year);
                foreach (var k in kq)
                {
                    revenue = revenue + k.Revenue;
                }
                revenueList.Add(new Revenue(i, revenue));
                revenue = 0;
            }
            
            return Ok(revenueList.ToList());
        }
        [HttpGet]
        [Authorize]
        [Route("revenue-by-year")]
        public IActionResult GetAllRevenueByYear()
        {
            List<Revenue> revenueList = new List<Revenue>();

            int revenue = 0;
            for (int i = DateTime.Now.Year; i >= DateTime.Now.Year - 10; i--)
            {
                    for (int j = 0; j < 12; j++)
                    {
                        var kq = db.CallCards.Join(db.Books, c => c.IdBook, b => b.Id, (c, b) => new
                        {
                            Revenue = b.Price,
                            BeginDate = c.BeginDate
                        })
                        .Where(d => d.BeginDate.Month == j && d.BeginDate.Year == i);
                        foreach (var k in kq)
                        {
                            revenue = revenue + k.Revenue;
                        }
                    }
                    revenueList.Add(new Revenue(i, revenue));
                    revenue = 0;
                }

            return Ok(revenueList.ToList());
        }
        [HttpGet]
        [Authorize]
        [Route("top-borrowed")]
        public IActionResult GetTopBorrowed()
        {
            List<BorrowedKind> list = new List<BorrowedKind>();
            var kq = db.Kinds
                .Join(db.Books, k => k.Id, b => b.IdKind, (k, b) => new
                {
                    namekind = k.Name,
                    idbook = b.Id                  
                })
                .Join(db.CallCards, t => t.idbook, c => c.IdBook, (t,c)=> new
                {
                    t.namekind
                }).GroupBy( n => n.namekind)
                .Select(n => new
                {
                    namekind=n.Key,
                    namekindCount=n.Count()
                });
            foreach (var item in kq)
            {
                list.Add(new BorrowedKind(item.namekind, item.namekindCount));
            }
            return Ok(list.ToList());
        }
        [HttpGet]     
        [Route("top-10-book")]
        public IActionResult GetAllTop10Book()
        {
            List<TopTenBook> list = new List<TopTenBook>();
            var kq = db.Kinds
                .Join(db.Books, k => k.Id, b => b.IdKind, (k, b) => new
                {
                    namekind = k.Name,
                    idbook = b.Id,
                    namebook=b.Name
                })
                .Join(db.CallCards, t => t.idbook, c => c.IdBook, (t, c) => new
                {
                    t.namebook
                }).GroupBy(n => n.namebook)
                .Select(n => new
                {
                    namebook = n.Key,
                    namebookcount = n.Count()
                })
                .Join(db.Books, t => t.namebook, b => b.Name, (t, b) => new
                {
                    nameofbook = t.namebook,
                    countofbook = t.namebookcount,
                    idkind = b.IdKind
                })
                .Join(db.Kinds, t => t.idkind, k=>k.Id, (t, k) => new
                {
                    bookname = t.nameofbook,
                    kindname = k.Name,
                    bookcount = t.countofbook
                }).OrderByDescending(n=>n.bookcount).Take(10);
            foreach (var item in kq)
            {
                list.Add(new TopTenBook(item.bookname, item.kindname, item.bookcount));
            }
            return Ok(list.ToList());
        }
    }
}

