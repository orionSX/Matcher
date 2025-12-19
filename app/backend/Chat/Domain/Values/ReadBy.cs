namespace Domain.Values
{
    public record ReadBy
    {
        public Guid UserId { get; set; }
        public DateTime? ReadAt { get; set; }
    }
}
