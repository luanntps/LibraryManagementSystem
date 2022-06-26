using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using pnlib.Models;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;

namespace PhuongNamLib.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class AuthenController : ControllerBase
    {
        public IConfiguration _configuration;
        private readonly PNLibEntities db;

        public AuthenController(IConfiguration config, PNLibEntities context)
        {
            _configuration = config;
            db = context;
        }

        //Đăng nhập để lấy token
        [Route("authentication")]
        [HttpGet]
        public async Task<IActionResult> GetToken(string ID, string password)
        {
                
            if (ID != null && password != null)
            {
                var user = await db.Librarians.FirstOrDefaultAsync(user => user.Id.ToLower() == ID.ToLower() && user.Password == password);

                if (user != null)
                {
                    var roles = (from r in db.Roles
                                 where r.Id == user.IdRole
                                 select r);
                    string role = null;
                    foreach (var roleClaim in roles)
                    {
                        role = roleClaim.Name;
                    }
                    //create claims details based on the user information
                    var claims = new[] {
                    new Claim(JwtRegisteredClaimNames.Sub, _configuration["Jwt:Subject"]),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                    new Claim(JwtRegisteredClaimNames.Iat, DateTime.Now.ToString()),
                    new Claim("Name", user.Name),
                    new Claim("Role", role),
                    new Claim("Id", user.Id)
                   };

                    var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]));

                    var signIn = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

                    var token = new JwtSecurityToken(_configuration["Jwt:Issuer"], _configuration["Jwt:Audience"], claims, expires: DateTime.Now.AddDays(1), signingCredentials: signIn);
                   
                    return Ok(new Authen(role,new JwtSecurityTokenHandler().WriteToken(token)));
                }
                else
                {
                    return BadRequest("Invalid credentials");
                }
            }
            else
            {
                return BadRequest();
            }
        }

       
        }
    }


